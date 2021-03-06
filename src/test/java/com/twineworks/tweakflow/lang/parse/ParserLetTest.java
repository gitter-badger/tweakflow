/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Twineworks GmbH
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

package com.twineworks.tweakflow.lang.parse;

import com.twineworks.tweakflow.lang.ast.expressions.BooleanNode;
import com.twineworks.tweakflow.lang.ast.expressions.ExpressionNode;
import com.twineworks.tweakflow.lang.ast.expressions.LetNode;
import com.twineworks.tweakflow.lang.ast.structure.ModuleNode;
import com.twineworks.tweakflow.lang.ast.structure.VarDefNode;
import com.twineworks.tweakflow.lang.load.loadpath.ResourceLocation;
import com.twineworks.tweakflow.lang.parse.units.ResourceParseUnit;
import com.twineworks.tweakflow.lang.types.Types;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.twineworks.tweakflow.lang.ast.NodeStructureAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

public class ParserLetTest {

  private HashMap<String, Map<String, VarDefNode>> moduleCache = new HashMap<>();

  private synchronized Map<String, VarDefNode> getVars(String ofModule) {
    if (!moduleCache.containsKey(ofModule)){
      Parser p = new Parser(
          new ResourceParseUnit(new ResourceLocation.Builder().build(), ofModule)
      );
      ParseResult result = p.parseUnit();

      if (result.isError()){
        result.getException().printDigestMessageAndStackTrace();
      }
      // parse is successful
      assertThat(result.isSuccess()).isTrue();

      // get the variable map
      Map<String, VarDefNode> varMap = ((ModuleNode) result.getNode()).getLibraries().get(0).getVars().getMap();
      moduleCache.put(ofModule, varMap);
    }
    return moduleCache.get(ofModule);

  }

  @Test
  public void parses_let() throws Exception {

    Map<String, VarDefNode> varDefMap = getVars("fixtures/tweakflow/analysis/parsing/semantic/let.tf");

    // bindings: let {a: 1} true
    ExpressionNode bindingsNode = varDefMap.get("bindings").getValueExpression();
    assertThat(bindingsNode).isInstanceOf(LetNode.class);
    LetNode bindingsLetNode = (LetNode) bindingsNode;

    LinkedHashMap<String, VarDefNode> bindings = bindingsLetNode.getBindings().getVars().getMap();
    assertThat(bindings).hasSize(1);
    assertThat(bindings.get("a").getDeclaredType()).isEqualTo(Types.ANY);
    assertThat(bindings.get("a").getSymbolName()).isEqualTo("a");

    BooleanNode bindingsValue = (BooleanNode) bindingsLetNode.getExpression();
    assertThat(bindingsValue.getBoolVal()).isEqualTo(Boolean.TRUE);

  }

}