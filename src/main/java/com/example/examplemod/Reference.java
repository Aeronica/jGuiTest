/*
 * Aeronica's mxTune MOD
 * Copyright 2019, Paul Boese a.k.a. Aeronica
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.example.examplemod;

import java.util.UUID;

public class Reference
{
    private Reference() {/* NOP */}

    public static final UUID EMPTY_UUID = new UUID(0L, 0L);
    public static final String MOD_ID = "examplemod";
    static final String MOD_NAME = "Example Mod";
    static final String VERSION = "{@VERSION}";
    static final String ACCEPTED_MINECRAFT_VERSIONS = "[1.12.2,1.13)";
    static final String DEPENDENTS = "required-after:forge@[1.12.2-14.23.5.2802,)";
}