#!/bin/bash
cd /home/kavia/workspace/code-generation/in-memory-notes-service-183170-183179/notes_backend
./gradlew checkstyleMain
LINT_EXIT_CODE=$?
if [ $LINT_EXIT_CODE -ne 0 ]; then
   exit 1
fi

