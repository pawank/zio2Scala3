# zio2Scala3

## Introduction 

An example project with ZIO 2.x and Scala 3.x with native platform build packages.


## GraalVM native image generation (tested on MacOSX)

Steps to generate native binary

1. Download graalvm from https://github.com/graalvm/graalvm-ce-builds/releases/tag/vm-20.3.4
2. `tar -zxvf graalvm-ce-java11-20.3.4\* -C .`
3. `export GRAALVM_HOME=<path to graalvm>/graalvm-ce-java11-20.3.4/Contents/Home`
4. `export JAVA_HOME=$GRAALVM_HOME`
5. `$GRAALVM_HOME/bin/gu install native-image`
6. `sudo xattr -r -d com.apple.quarantine <path to graalvm>/graalvm-ce-java11-20.3.4` 

The above steps are needed only for the first time.

Set the environment variable before starting `sbt`.

`export NATIVE_IMAGE_INSTALLED=true`
