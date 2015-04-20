package org.apache.solr.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ctc.wstx.io.CharsetNames;
import org.apache.solr.core.CoreContainer;
import org.apache.solr.core.SolrConfig;
import org.apache.solr.core.SolrCore;
import org.apache.solr.handler.component.SearchComponent;
import org.apache.solr.handler.component.SearchHandler;
import org.apache.solr.request.SolrRequestHandler;
import org.apache.solr.servlet.json.ConfigFileDTO;
import org.apache.solr.servlet.json.ResponseDTO;
import org.apache.solr.servlet.json.SearchHandlerDTO;
import org.apache.solr.servlet.json.UpdateHandlerDTO;
import org.apache.solr.update.UpdateHandler;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Project master thesis 2015 (Mt15)
 * <p/>
 *
 * Created by onur kasimlar on 06.01.15.
 */
public class MT15HelperImpl {

  public static String REQUEST_PATH = "/readSettings";
  public static String UPDATE_PATH = "/updatePath";
  public static String REQUEST_CONFIG_FILES = "/configFiles";

  private Map<String, ConfigFileDTO> resultMap = new HashMap<String, ConfigFileDTO>();

  static final Logger log = LoggerFactory.getLogger(MT15HelperImpl.class);

  /**
   * Read properties from solrconfig.xml and send them to the caller as JSON.
   *
   * @param resp servlet response used to send the JSON with properties
   */
  public void readSolrProperties(HttpServletRequest req, HttpServletResponse resp, SolrCore core) {
    Map<String, SolrRequestHandler> requestHandlers = core.getRequestHandlers();
    UpdateHandler updateHandler = core.getUpdateHandler();
    List<SearchHandlerDTO> searchComponentNames = new ArrayList<>();

    String param = getParam(req);

    for (SolrRequestHandler requestHandler : requestHandlers.values()) {
      if (requestHandler instanceof SearchHandler) {
        SearchHandler searchHandler = (SearchHandler) requestHandler;
        List<String> componentNames = new ArrayList<>();
        List<String> defaultComponentNames = new ArrayList<>();
        for (SearchComponent component : searchHandler.getComponents()) {
          componentNames.add(component.getName());
        }
        List<String> defaultComponents = searchHandler.getDefaultComponents();
        for (String defaultComponent : defaultComponents) {
          defaultComponentNames.add(defaultComponent);
        }

        List<String> first = (List<String>) searchHandler.getInitArgs().get("first-components");
        List<String> last  = (List<String>) searchHandler.getInitArgs().get("last-components");
        searchComponentNames.add(new SearchHandlerDTO(searchHandler.getName(), componentNames, defaultComponentNames, first, last));
      }
    }

    SolrConfig.UpdateHandlerInfo updateHandlerInfo = core.getSolrConfig().getUpdateHandlerInfo();
    final int autoCommitMaxDocs = updateHandlerInfo.autoCommmitMaxDocs;
    final int autoCommitMaxTime = updateHandlerInfo.autoCommmitMaxTime;
    final int autoSoftCommitMaxDocs = updateHandlerInfo.autoSoftCommmitMaxDocs;
    final int autoSoftCommitMaxTime = updateHandlerInfo.autoSoftCommmitMaxTime;
    final boolean openSearcher = updateHandlerInfo.openSearcher;
    final String className = updateHandlerInfo.className;
    final int commitIntervalLowerBound = updateHandlerInfo.commitIntervalLowerBound;
    final boolean commitWithinSoftCommit = updateHandlerInfo.commitWithinSoftCommit;
    final boolean indexWriterCloseWaitsForMerges = updateHandlerInfo.indexWriterCloseWaitsForMerges;

    UpdateHandlerDTO updateHandlerDTO = new UpdateHandlerDTO(autoCommitMaxDocs,autoCommitMaxTime,autoSoftCommitMaxDocs,
        autoSoftCommitMaxTime,openSearcher,className,commitIntervalLowerBound,commitWithinSoftCommit,indexWriterCloseWaitsForMerges);


    InputStream inputStream = null;
    StringBuilder solrConfigSB = new StringBuilder();
    try {
      inputStream = core.getSolrConfig().getResourceLoader().openConfig(core.getSolrConfig().getName());
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      String line;
      while ((line = reader.readLine()) != null) {
          solrConfigSB.append(line).append("\n");
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

    String solrConfigContent = solrConfigSB.toString();
    final ResponseDTO responseDTO = new ResponseDTO(searchComponentNames, updateHandlerDTO, solrConfigContent);


    resp.setContentType("application/javascript");
    resp.setCharacterEncoding("UTF-8");

    resp.setStatus(HttpServletResponse.SC_OK);

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);

    PrintWriter writer = null;
    try {
      writer = resp.getWriter();
      String json = mapper.writeValueAsString(responseDTO);

      writer.write(new String((param + "(").getBytes()));
      writer.write(json);
      writer.write(new String(");".getBytes()));
    } catch (IOException e) {
      log.info(e.getMessage());
      e.printStackTrace();
    }

    if (writer != null)
      writer.close();


  }

  private String getParam(HttpServletRequest req) {
    for (String[] entry : req.getParameterMap().values()) {
      for (String en : entry) {
        log.info("param: " + en);
        return en;
      }
    }
    return "";
  }

  public void updateSolrProperties(HttpServletRequest req, HttpServletResponse resp, SolrCore core) {
    try (BufferedReader reader = req.getReader() ) {

      StringBuilder sb = new StringBuilder();
      String line = null;
      while ((line = reader.readLine()) != null) {
        sb.append(line).append("\n");
      }
      String solrConfigContent = sb.toString();
      log.info("solrConfigInfo: " + solrConfigContent);
      reader.close();

      // write solrConfigContent into file
      final String resourceName = core.getSolrConfig().getName();
      File file = new File(core.getSolrConfig().getResourceLoader().getConfigDir() + resourceName).getAbsoluteFile();
      log.info("url: " + file.getAbsolutePath());
      FileWriter writer = new FileWriter(file);
      BufferedWriter bw = new BufferedWriter(writer);
      bw.write(solrConfigContent);
      bw.close();
      log.info("wrote new solrconfig.xml: " + solrConfigContent);
    } catch (IOException e) {
      e.printStackTrace();
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      return;
    }
    resp.setStatus(HttpServletResponse.SC_OK);

  }

  public void readSettingsFolder(HttpServletRequest req, HttpServletResponse resp, SolrCore core) {
    final Map<String, String> propertiesMap = core.getSolrConfig().readMT15Settings().getPropertiesMap();
    String folderPath = propertiesMap.get("mt15.settings.folder.path");
    log.info("mt15.settings.folder.path=" + folderPath);
    File folder = new File("/home/onur/master/x12/settings/solr");

    for (File file : folder.listFiles()) {
      final String fileName = file.getName();
      if (fileName.endsWith("~")) continue;
      if (resultMap.containsKey(fileName)) continue;
      String description = getFileDescription(file);
      log.info("description: {}", description);
      ConfigFileDTO configFileDTO = new ConfigFileDTO(fileName, description);
      resultMap.put(fileName, configFileDTO);
    }

    String param = getParam(req);
    resp.setContentType("application/javascript");
    resp.setCharacterEncoding("UTF-8");

    resp.setStatus(HttpServletResponse.SC_OK);

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);

    PrintWriter writer = null;
    try {
      writer = resp.getWriter();
      String json = mapper.writeValueAsString(resultMap.values());

      writer.write(new String((param + "(").getBytes()));
      writer.write(json);
      writer.write(new String(");".getBytes()));
    } catch (IOException e) {
      log.info(e.getMessage());
      e.printStackTrace();
    }

    if (writer != null)
      writer.close();


  }

  private String getFileDescription(File file) {
    StringBuilder builder = new StringBuilder();
    try {
      boolean write = false;
      for (String line : Files.readAllLines(Paths.get(file.getPath()), Charset.forName(CharsetNames.CS_UTF8))) {
        line = line.trim();

        if (!write && line.contains("start_description")) {
          write = true;
          continue;
        }
        if (write) {
          if (line.contains("end_description"))
            break;
          builder.append(line);
        }
      }
      String description = builder.toString();
      log.info("getFileDescription: " + description);
      return description.replaceAll("\\*", " ");
    } catch (IOException e) {
      log.error("error in getFileDescription: " + e.getMessage());
      e.printStackTrace();
    }
    return "";
  }

  public SolrCore getCoreByPath( CoreContainer cores, String path) {
    String corename = path.split("/")[1];
    log.info("corename: " + corename);
    return cores.getCore(corename);
  }
}
