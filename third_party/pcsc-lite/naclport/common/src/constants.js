/**
 * @license
 * Copyright 2016 Google Inc.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/**
 * @fileoverview Constants common to both PC/SC-Lite server-side and
 * client-side.
 */

goog.provide('GoogleSmartCard.PcscLiteCommon.Constants');

goog.scope(function() {

const GSC = GoogleSmartCard;

const Constants = GSC.PcscLiteCommon.Constants;

/** @const */
Constants.SERVER_OFFICIAL_APP_ID = 'khpfeaanjngmcnplbdlpegiifgpfgdco';

goog.exportSymbol(
    'GoogleSmartCard.PcscLiteCommon.Constants.SERVER_OFFICIAL_APP_ID',
    Constants.SERVER_OFFICIAL_APP_ID);

/** @const */
Constants.REQUESTER_TITLE = 'pcsc_lite_function_call';

// The constant from the PC/SC-Lite API docs.
/** @const */
Constants.PNP_NOTIFICATION = String.raw`\\?PnP?\Notification`;

// The constant taken from PCSC implementation.
// Id of the attribute signaling that device has been removed.
/** @const */
Constants.TAG_IFD_DEVICE_REMOVED = 0x0FB4;
});  // goog.scope
