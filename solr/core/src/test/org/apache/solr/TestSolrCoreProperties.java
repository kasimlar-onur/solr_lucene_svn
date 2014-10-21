/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.solr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.util.IOUtils;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.common.util.NamedList;
import org.junit.BeforeClass;

import java.nio.charset.StandardCharsets;

/**
 * <p> Test for Loading core properties from a properties file </p>
 *
 *
 * @since solr 1.4
 */
public class TestSolrCoreProperties extends SolrJettyTestBase {

  @BeforeClass
  public static void beforeTest() throws Exception {
    File homeDir = createTempDir().toFile();

    File collDir = new File(homeDir, "collection1");
    File dataDir = new File(collDir, "data");
    File confDir = new File(collDir, "conf");

    homeDir.mkdirs();
    collDir.mkdirs();
    dataDir.mkdirs();
    confDir.mkdirs();

    FileUtils.copyFile(new File(SolrTestCaseJ4.TEST_HOME(), "solr.xml"), new File(homeDir, "solr.xml"));
    String src_dir = TEST_HOME() + "/collection1/conf";
    FileUtils.copyFile(new File(src_dir, "schema-tiny.xml"), 
                       new File(confDir, "schema.xml"));
    FileUtils.copyFile(new File(src_dir, "solrconfig-solcoreproperties.xml"), 
                       new File(confDir, "solrconfig.xml"));
    FileUtils.copyFile(new File(src_dir, "solrconfig.snippet.randomindexconfig.xml"), 
                       new File(confDir, "solrconfig.snippet.randomindexconfig.xml"));

    Properties p = new Properties();
    p.setProperty("foo.foo1", "f1");
    p.setProperty("foo.foo2", "f2");
    Writer fos = new OutputStreamWriter(new FileOutputStream(new File(confDir, "solrcore.properties")), StandardCharsets.UTF_8);
    p.store(fos, null);
    IOUtils.close(fos);

    createJetty(homeDir.getAbsolutePath(), null, null);
  }

  public void testSimple() throws Exception {
    SolrParams params = params("q", "*:*", 
                               "echoParams", "all");
    QueryResponse res = getSolrServer().query(params);
    assertEquals(0, res.getResults().getNumFound());

    NamedList echoedParams = (NamedList) res.getHeader().get("params");
    assertEquals("f1", echoedParams.get("p1"));
    assertEquals("f2", echoedParams.get("p2"));
  }

}