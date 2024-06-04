#!/bin/sh
#
# Copyright 2022-2024 benelog GmbH & Co. KG
#
#     Licensed under the Apache License, Version 2.0 (the "License");
#     you may not use this file except in compliance with the License.
#     You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
#     Unless required by applicable law or agreed to in writing, software
#     distributed under the License is distributed on an "AS IS" BASIS,
#     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#     See the License for the specific language governing permissions and
#     limitations under the License.
#

SCRIPT_PATH=`dirname $0`
find $SCRIPT_PATH/../src/main/resources -type f | sed -e 's/^.*\/src\/main\/resources//g' | grep -v 'openepcis-test-resources.list' | sort > $SCRIPT_PATH/../src/main/resources/openepcis-test-resources.list