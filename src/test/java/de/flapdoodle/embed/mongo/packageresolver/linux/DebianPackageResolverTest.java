/**
 * Copyright (C) 2011
 *   Michael Mosmann <michael@mosmann.de>
 *   Martin Jöhren <m.joehren@googlemail.com>
 *
 * with contributions from
 * 	konstantin-ba@github,Archimedes Trajano	(trajano@github)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.flapdoodle.embed.mongo.packageresolver.linux;

import de.flapdoodle.embed.mongo.Command;
import de.flapdoodle.embed.mongo.packageresolver.HtmlParserResultTester;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.distribution.Version;
import de.flapdoodle.os.CommonArchitecture;
import de.flapdoodle.os.ImmutablePlatform;
import de.flapdoodle.os.OS;
import de.flapdoodle.os.Platform;
import de.flapdoodle.os.linux.DebianVersion;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DebianPackageResolverTest {
  /*
    Debian 9 x64
    https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-debian92-{}.tgz
    3.6.23 - 5.0.4
  */
  @ParameterizedTest
  @ValueSource(strings = {"5.0.5", "5.0.2 - 5.0.0", "4.4.11", "4.4.9 - 4.4.0", "4.2.18", "4.2.16 - 4.2.5", "4.2.3 - 4.2.0", "4.0.27 - 4.0.0", "3.6.23 - 3.6.5"})
  public void debian9x64(String version) {
    assertThat(linuxWith(CommonArchitecture.X86_64, DebianVersion.DEBIAN_9), version)
            .resolvesTo("/linux/mongodb-linux-x86_64-debian92-{}.tgz");
  }

  /*
    Debian 10 x64
    https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-debian10-{}.tgz
    4.2.13 - 5.0.4
  */
  @ParameterizedTest
  @ValueSource(strings = {"5.0.5", "5.0.2 - 5.0.0", "4.4.11", "4.4.9 - 4.4.0", "4.2.18", "4.2.16 - 4.2.5", "4.2.3 - 4.2.1"})
  public void debian10x64(String version) {
    assertThat(linuxWith(CommonArchitecture.X86_64, DebianVersion.DEBIAN_10), version)
            .resolvesTo("/linux/mongodb-linux-x86_64-debian10-{}.tgz");
  }

  private static Platform linuxWith(CommonArchitecture architecture, de.flapdoodle.os.Version version) {
    return ImmutablePlatform.builder()
            .operatingSystem(OS.Linux)
            .architecture(architecture)
            .version(version)
            .build();
  }

  private static HtmlParserResultTester assertThat(Platform platform, String versionList) {
    return HtmlParserResultTester.with(
            new DebianPackageResolver(Command.Mongo),
            version -> Distribution.of(Version.of(version), platform),
            versionList);
  }

}