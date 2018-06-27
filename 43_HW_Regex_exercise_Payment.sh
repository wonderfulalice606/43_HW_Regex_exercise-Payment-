#!/bin/sh

#===========================================================================================
GITHUB=wonderfulalice606
WS_DIR=workspace
DOC=Documents
REPO=43_HW_Regex_exercise_Payment
RUNNER=Test_Runner

#===========================================================================================

if ! which java > /dev/null 2>&1; then echo Java is not installed; return; fi
if ! which mvn > /dev/null 2>&1; then echo Maven is not installed; return; fi
if ! which git > /dev/null 2>&1; then echo Git is not installed; return; fi

if [ -d "$HOME/$DOC/$WS_DIR/" ]; then cd ~/$DOC/$WS_DIR/$RUNNER; else echo $WS_DIR is not exist; return; fi
if [ -d "$HOME/$DOC/$WS_DIR/$RUNNER/$REPO" ]; then rm -rf $HOME/$DOC/$WS_DIR/$RUNNER/$REPO; fi

git clone https://github.com/$GITHUB/$REPO.git
cd ./$REPO

mvn package -Dbuild.version="1.1" -Dmain.Class="core.Chrome"

java -jar ./target/43_HW_Regex_exercise_Payment-1.0-jar-with-dependencies.jar

mvn package -Dbuild.version="1.2" -Dmain.Class="core.Firefox"

java -jar ./target/43_HW_Regex_exercise_Payment-1.0-jar-with-dependencies.jar

mvn package -Dbuild.version="1.3" -Dmain.Class="core.Safari"

java -jar ./target/43_HW_Regex_exercise_Payment-1.0-jar-with-dependencies.jar

mvn package -Dbuild.version="1.4" -Dmain.Class="core.HtnlUnit"

java -jar ./target/43_HW_Regex_exercise_Payment-1.0-jar-with-dependencies.jar



echo "$HOME/$DOC/$WS_DIR/$RUNNER/$REPO"
