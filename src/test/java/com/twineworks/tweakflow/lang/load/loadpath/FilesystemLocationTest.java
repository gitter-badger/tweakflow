/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Twineworks GmbH
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.twineworks.tweakflow.lang.load.loadpath;

import com.twineworks.tweakflow.lang.errors.LangException;
import org.junit.Test;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class FilesystemLocationTest {

  private static String assetDir = "src/test/resources/fixtures/tweakflow/loading/on_path";

  @Test
  public void strict_finds_on_path_file() throws Exception {
    FilesystemLocation loc = new FilesystemLocation(Paths.get(assetDir), true);
    assertThat(loc.pathExists("module.tf")).isTrue();
  }

  @Test
  public void strict_finds_on_path_relative_file() throws Exception {
    FilesystemLocation loc = new FilesystemLocation(Paths.get(assetDir), true);
    assertThat(loc.pathExists("./module.tf")).isTrue();
  }

  @Test
  public void strict_finds_on_path_default_extension_file() throws Exception {
    FilesystemLocation loc = new FilesystemLocation(Paths.get(assetDir), true);
    assertThat(loc.pathExists("module")).isTrue();
  }


  @Test
  public void strict_finds_on_path_custom_extension_file() throws Exception {
    FilesystemLocation loc = new FilesystemLocation(Paths.get(assetDir), true, ".ext");
    assertThat(loc.pathExists("custom")).isTrue();
  }

  @Test
  public void strict_finds_on_path_back_relative_file() throws Exception {
    FilesystemLocation loc = new FilesystemLocation(Paths.get(assetDir), true);
    assertThat(loc.pathExists("../on_path/module.tf")).isTrue();
  }

  @Test
  public void strict_refuses_off_path_file() throws Exception {
    FilesystemLocation loc = new FilesystemLocation(Paths.get(assetDir), true);
    assertThat(loc.pathExists("../off_path/module.tf")).isFalse();
  }

  @Test
  public void strict_finds_absolute_file() throws Exception {
    FilesystemLocation loc = new FilesystemLocation(Paths.get(assetDir), true);
    assertThat(loc.pathExists(Paths.get(".").toAbsolutePath().toString()+"/"+assetDir+"/module.tf")).isTrue();
  }

  @Test
  public void non_strict_finds_on_path_file() throws Exception {
    FilesystemLocation loc = new FilesystemLocation(Paths.get(assetDir), false);
    assertThat(loc.pathExists("module.tf")).isTrue();
  }

  @Test
  public void non_strict_finds_on_path_relative_file() throws Exception {
    FilesystemLocation loc = new FilesystemLocation(Paths.get(assetDir), false);
    assertThat(loc.pathExists("./module.tf")).isTrue();
  }

  @Test
  public void non_strict_finds_on_path_default_extension_file() throws Exception {
    FilesystemLocation loc = new FilesystemLocation(Paths.get(assetDir), false);
    assertThat(loc.pathExists("module")).isTrue();
  }


  @Test
  public void non_strict_finds_on_path_custom_extension_file() throws Exception {
    FilesystemLocation loc = new FilesystemLocation(Paths.get(assetDir), false, ".ext");
    assertThat(loc.pathExists("custom")).isTrue();
  }

  @Test
  public void non_strict_finds_on_path_back_relative_file() throws Exception {
    FilesystemLocation loc = new FilesystemLocation(Paths.get(assetDir), false);
    assertThat(loc.pathExists("../on_path/module.tf")).isTrue();
  }

  @Test
  public void non_strict_finds_off_path_file() throws Exception {
    FilesystemLocation loc = new FilesystemLocation(Paths.get(assetDir), false);
    assertThat(loc.pathExists("../off_path/module.tf")).isTrue();
  }

  @Test
  public void non_strict_finds_absolute_file() throws Exception {
    FilesystemLocation loc = new FilesystemLocation(Paths.get(assetDir), false);
    assertThat(loc.pathExists(Paths.get(".").toAbsolutePath().toString()+"/"+assetDir+"/module.tf")).isTrue();
  }


  ///

  @Test
  public void strict_finds_unit_on_path_file() throws Exception {
    FilesystemLocation loc = new FilesystemLocation(Paths.get(assetDir), true);
    assertThat(loc.getParseUnit("module.tf")).isNotNull();
  }

  @Test
  public void strict_finds_unit_on_path_relative_file() throws Exception {
    FilesystemLocation loc = new FilesystemLocation(Paths.get(assetDir), true);
    assertThat(loc.getParseUnit("./module.tf")).isNotNull();
  }

  @Test
  public void strict_finds_unit_on_path_default_extension_file() throws Exception {
    FilesystemLocation loc = new FilesystemLocation(Paths.get(assetDir), true);
    assertThat(loc.getParseUnit("module")).isNotNull();
  }


  @Test
  public void strict_finds_unit_on_path_custom_extension_file() throws Exception {
    FilesystemLocation loc = new FilesystemLocation(Paths.get(assetDir), true, ".ext");
    assertThat(loc.getParseUnit("custom")).isNotNull();
  }

  @Test
  public void strict_finds_unit_on_path_back_relative_file() throws Exception {
    FilesystemLocation loc = new FilesystemLocation(Paths.get(assetDir), true);
    assertThat(loc.getParseUnit("../on_path/module.tf")).isNotNull();
  }

  @Test(expected = LangException.class)
  public void strict_unit_refuses_off_path_file() throws Exception {
    FilesystemLocation loc = new FilesystemLocation(Paths.get(assetDir), true);
    loc.getParseUnit("../off_path/module.tf");
  }

  @Test
  public void strict_finds_unit_absolute_file() throws Exception {
    FilesystemLocation loc = new FilesystemLocation(Paths.get(assetDir), true);
    assertThat(loc.getParseUnit(Paths.get(".").toAbsolutePath().toString()+"/"+assetDir+"/module.tf")).isNotNull();
  }

  @Test
  public void non_strict_finds_unit_on_path_file() throws Exception {
    FilesystemLocation loc = new FilesystemLocation(Paths.get(assetDir), false);
    assertThat(loc.getParseUnit("module.tf")).isNotNull();
  }

  @Test
  public void non_strict_finds_unit_on_path_relative_file() throws Exception {
    FilesystemLocation loc = new FilesystemLocation(Paths.get(assetDir), false);
    assertThat(loc.getParseUnit("./module.tf")).isNotNull();
  }

  @Test
  public void non_strict_finds_unit_on_path_default_extension_file() throws Exception {
    FilesystemLocation loc = new FilesystemLocation(Paths.get(assetDir), false);
    assertThat(loc.getParseUnit("module")).isNotNull();
  }


  @Test
  public void non_strict_finds_unit_on_path_custom_extension_file() throws Exception {
    FilesystemLocation loc = new FilesystemLocation(Paths.get(assetDir), false, ".ext");
    assertThat(loc.getParseUnit("custom")).isNotNull();
  }

  @Test
  public void non_strict_finds_unit_on_path_back_relative_file() throws Exception {
    FilesystemLocation loc = new FilesystemLocation(Paths.get(assetDir), false);
    assertThat(loc.getParseUnit("../on_path/module.tf")).isNotNull();
  }

  @Test
  public void non_strict_finds_unit_off_path_file() throws Exception {
    FilesystemLocation loc = new FilesystemLocation(Paths.get(assetDir), false);
    assertThat(loc.getParseUnit("../off_path/module.tf")).isNotNull();
  }

  @Test
  public void non_strict_finds_unit_absolute_file() throws Exception {
    FilesystemLocation loc = new FilesystemLocation(Paths.get(assetDir), false);
    assertThat(loc.getParseUnit(Paths.get(".").toAbsolutePath().toString()+"/"+assetDir+"/module.tf")).isNotNull();
  }



}