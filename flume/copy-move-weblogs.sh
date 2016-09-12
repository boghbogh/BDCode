#!/bin/bash

# This script copies files from one location into a temporary directory
# and then moves them from there into a final location. This is needed
# in order to support students being able to run -- and re-run -- this
# exercise reliably. Why? Because if we just had students move files 
# from the original location to the final one, then you could not re-run
# the exercise (i.e. the files would then be in a different directory, 
# and for that matter, would be renamed by Flume after being processed).
# So why won't it work to simply copy the files? Because copying is not
# an atomic operation, which means that Flume may try to process the file
# before the write is complete (it notices this, however, and warns you
# about it). Thus, the copy-then-move operation used by the script is a
# workaround; it is idempotent (due to the original copy operation) and 
# yet atomic (due to the subsequent move operation).

if [ -z "$1" ]
then
  echo "Usage: `basename $0` spooldirpath"
  exit 1
fi

echo "Copying and moving files"

TMPWEBLOGS=$BDDATADIR/tmp_weblogs

cp -rf $BDDATADIR/weblogs $TMPWEBLOGS
mv $TMPWEBLOGS/* $1
rm -rf $TMPWEBLOGS
