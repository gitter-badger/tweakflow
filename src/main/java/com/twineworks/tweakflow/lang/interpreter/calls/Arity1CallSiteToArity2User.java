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

package com.twineworks.tweakflow.lang.interpreter.calls;

import com.twineworks.tweakflow.lang.ast.Node;
import com.twineworks.tweakflow.lang.types.Type;
import com.twineworks.tweakflow.lang.values.*;
import com.twineworks.tweakflow.lang.interpreter.Stack;
import com.twineworks.tweakflow.lang.interpreter.StackEntry;
import com.twineworks.tweakflow.lang.interpreter.memory.LocalMemorySpace;

import java.util.Collections;

public class Arity1CallSiteToArity2User implements Arity1CallSite {

  private final Arity2UserFunction f;
  private final Stack stack;
  private final UserCallContext userCallContext;

  private final StackEntry stackEntry;
  private final Type p0Type;

  private final Value defArg1;
  private final Type retType;

  public Arity1CallSiteToArity2User(UserFunctionValue f, Node at, Stack stack, UserCallContext userCallContext) {

    this.stack = stack;
    this.userCallContext = userCallContext;
    this.f = (Arity2UserFunction) f.getUserFunction();

    FunctionParameter[] params = f.getSignature().getParameterArray();
    p0Type = params[0].getDeclaredType();

    defArg1 = params[1].getDefaultValue();
    retType = f.getSignature().getReturnType();

    stackEntry = new StackEntry(at, LocalMemorySpace.EMPTY, Collections.emptyMap());

  }

  @Override
  public Value call(Value arg0) {
    stack.push(stackEntry);
    Value retValue = f.call(userCallContext, arg0.castTo(p0Type), defArg1).castTo(retType);
    stack.pop();
    return retValue;
  }
}
