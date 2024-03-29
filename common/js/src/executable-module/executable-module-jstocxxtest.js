/**
 * @license
 * Copyright 2023 Google Inc. All Rights Reserved.
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

/**
 * @fileoverview This file contains tests for the executable module wrapper.
 */

goog.require('GoogleSmartCard.ExecutableModule');
goog.require('GoogleSmartCard.IntegrationTestController');
goog.require('goog.testing.asserts');
goog.require('goog.testing.jsunit');

goog.setTestOnly();

goog.scope(function() {

const GSC = GoogleSmartCard;

/** @type {GSC.IntegrationTestController?} */
let testController;

goog.exportSymbol('testExecutableModule', {
  'setUp': async function() {
    // Set up the controller and load the C/C++ executable module.
    testController = new GSC.IntegrationTestController();
    await testController.initAsync();
  },

  'tearDown': async function() {
    try {
      await testController.disposeAsync();
    } finally {
      testController = null;
    }
  },

  // Test that after the C++ code crashes via `GOOGLE_SMART_CARD_CHECK()`, the
  // JS module object gets disposed of.
  'testCrashViaCheck': async function() {
    await testController.setUpCppHelper(
        'LoggingTestHelper', /*helperArgument=*/ {});

    try {
      await testController.sendMessageToCppHelper(
          'LoggingTestHelper', 'crash-via-check');
    } catch (e) {
      // This is expected branch - discard the exception.
      assert(testController.executableModule.isDisposed());
      return;
    }

    fail('Unexpectedly proceeded beyond crash');
  },

  // Test that after the C++ code crashes via `GOOGLE_SMART_CARD_LOG_FATAL`, the
  // JS module object gets disposed of.
  'testCrashViaFatalLog': async function() {
    await testController.setUpCppHelper(
        'LoggingTestHelper', /*helperArgument=*/ {});

    try {
      await testController.sendMessageToCppHelper(
          'LoggingTestHelper', 'crash-via-fatal-log');
    } catch (e) {
      // This is expected branch - discard the exception.
      assert(testController.executableModule.isDisposed());
      return;
    }

    fail('Unexpectedly proceeded beyond crash');
  },
});
});
