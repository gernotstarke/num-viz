package org.circulizr.configuration

import spock.lang.Specification

class ConfigurationSpec extends Specification {

    def "can configure numeric valueSet"() {
        given:
        def config = new ConfigSlurper().parse(""" valueSet = []  """ )
        Configuration configuration = new Configuration( config )

        expect:
        false
    }


    def "can configure typesafe PRODUCTION RunMode"() {
        given:
        def config = new ConfigSlurper().parse("""runmode = "PRODUCTION"  """ )
        Configuration configuration = new Configuration( config )

        expect:
        RunMode.PRODUCTION == configuration.RUNMODE
    }


    def "can configure typesafe DEBUG RunMode"() {
        given:
        def config = new ConfigSlurper().parse("""runmode = "DEBUG"  """ )
        Configuration configuration = new Configuration( config )

        expect:
        RunMode.DEBUG == configuration.RUNMODE
    }


}

/************************************************************************
 * This is free software - without ANY guarantee!
 *
 *
 * Copyright Dr. Gernot Starke, arc42.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *********************************************************************** */
