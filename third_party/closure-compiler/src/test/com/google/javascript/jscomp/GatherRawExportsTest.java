/*
 * Copyright 2009 The Closure Compiler Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.javascript.jscomp;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/** Tests for {@link GatherRawExports}. */
@RunWith(JUnit4.class)
public final class GatherRawExportsTest extends CompilerTestCase {

  private static final String EXTERNS = "var window;var self;";
  private GatherRawExports last;

  public GatherRawExportsTest() {
    super(EXTERNS);
  }

  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();
    enableNormalize();
    // TODO(bradfordcsmith): Stop normalizing the expected output or document why it is necessary.
    enableNormalizeExpectedOutput();
  }

  @Override
  protected CompilerPass getProcessor(Compiler compiler) {
    last = new GatherRawExports(compiler);
    return last;
  }

  @Test
  public void testTopLevelVar() {
    // Global vars are not what we are looking for.
    assertExported("var a");
  }

  @Test
  public void testExportsFoundQuoted() {
    assertExported("window['a']", "a");
  }

  @Test
  public void testExportsFoundUnquoted() {
    assertExported("window.a", "a");
  }

  @Test
  public void testExportWithOptChainFoundQuoted() {
    assertExported("window?.['a']", "a");
  }

  @Test
  public void testExportWithOptChainFoundUnquoted() {
    assertExported("window?.a", "a");
  }

  @Test
  public void testExportsFound4() {
    assertExported("this['a']", "a");
  }

  @Test
  public void testExportsFound5() {
    assertExported("this.a", "a");
  }

  @Test
  public void testExportsFound6() {
    assertExported("function f() { this['a'] }");
  }

  @Test
  public void testExportsFound7() {
    assertExported("function f() { this.a }");
  }

  @Test
  public void testExportsFound8() {
    assertExported("window['foo']", "foo");
  }

  @Test
  public void testExportsFound9() {
    assertExported("window['a'] = 1;", "a");
  }

  @Test
  public void testExportsFound10() {
    assertExported("window['a']['b']['c'] = 1;", "a");
  }

  @Test
  public void testExportsFound11() {
    assertExported("if (window['a'] = 1) alert(x);", "a");
  }

  @Test
  public void testExportsFound12() {
    assertExported("function foo() { window['a'] = 1; }", "a");
  }

  @Test
  public void testExportsFound13() {
    assertExported("function foo() {var window; window['a'] = 1; }");
  }

  @Test
  public void testExportsFound14() {
    assertExported("var a={window:{}}; a.window['b']");
  }

  @Test
  public void testExportsFound15() {
    assertExported("window.window['b']", "window");
  }

  @Test
  public void testExportsFound16() {
    // It would be nice to handle this case, hopefully inlining will take care
    // of it for us.
    assertExported("var a = window; a['b']");
  }

  @Test
  public void testExportsFound17() {
    // Gather "this" reference in a global if block.
    assertExported("if (true) { this.a }", "a");
    // Does not gather "this" reference in a local if block.
    assertExported("function f() { if (true) { this.a } }");
  }

  @Test
  public void testExportOnTopFound1() {
    assertExported("top['a']", "a");
  }

  @Test
  public void testExportOntopFound2() {
    assertExported("top.a", "a");
  }

  @Test
  public void testExportOnGlobalThis1() {
    assertExported("globalThis['a']", "a");
  }

  @Test
  public void testExportOnGlobalThis2() {
    assertExported("globalThis.a", "a");
  }

  @Test
  public void testExportOnPolyfillGlobal1() {
    assertExported("$jscomp.global['a']", "a");
  }

  @Test
  public void testExportOnPolyfillGlobal2() {
    assertExported("$jscomp.global.a", "a");
  }

  @Test
  public void testExportOnPolyfillGlobal1Collapsed() {
    assertExported("$jscomp$global['a']", "a");
  }

  @Test
  public void testExportOnPolyfillGlobal2Collapsed() {
    assertExported("$jscomp$global.a", "a");
  }

  @Test
  public void testExportOnSelf1() {
    assertExported("self['a']", "a");
  }

  @Test
  public void testExportOnSelf2() {
    assertExported("self.a", "a");
  }

  @Test
  public void testNoExportOnLocalSelf1() {
    assertExported("function fn(self) { self['a']; }");
  }

  @Test
  public void testNoExportOnLocalSelf2() {
    assertExported("function fn(self) { self.a; }");
  }

  @Test
  public void testExportOnGoogGlobalFound1() {
    assertExported("goog.global['a']", "a");
  }

  @Test
  public void testExportOnGoogGlobalFound2() {
    assertExported("goog.global.a", "a");
  }

  @Test
  public void testExportOnGoogGlobalFound3() {
    assertExported("goog$global['a']", "a");
  }

  @Test
  public void testExportOnGoogGlobalFound4() {
    assertExported("goog$global.a", "a");
  }

  private void assertExported(String js, String... names) {
    ImmutableSet<String> setNames = ImmutableSet.copyOf(names);
    testSame(js);
    assertThat(last.getExportedVariableNames()).isEqualTo(setNames);
  }
}
