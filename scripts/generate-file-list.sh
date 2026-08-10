#!/bin/sh
#
# Copyright 2022-2026 benelog GmbH & Co. KG
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
RESOURCES=$SCRIPT_PATH/../core/src/main/resources

# LC_ALL=C keeps the order stable no matter which locale the committer runs under
find $RESOURCES -type f | sed -e 's/^.*\/src\/main\/resources//g' | grep -v 'openepcis-test-resources.list' | LC_ALL=C sort > $RESOURCES/openepcis-test-resources.list