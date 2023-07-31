#!/bin/sh
SCRIPT_PATH=`dirname $0`
find $SCRIPT_PATH/../src/main/resources -type f | sed -e 's/^.*\/src\/main\/resources//g' | grep -v 'openepcis-test-resources.list' | sort > $SCRIPT_PATH/../src/main/resources/openepcis-test-resources.list