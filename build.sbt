Global / onChangedBuildSource := ReloadOnSourceChanges

name    := "EVOTestApi"
version := "0.0.1-SNAPSHOT"

scalaVersion := "2.13.5"
scalacOptions ++= Seq(
  "-deprecation",
  "-Xfatal-warnings",
  "-Wunused",
)

libraryDependencies ++= Seq(
  "com.lihaoyi"           %% "pprint"                   % "0.6.1",
  "com.lihaoyi"           %% "fansi"                    % "0.2.10",
  "com.lihaoyi"           %% "upickle"                  % "1.2.3",
  "org.scalatest"         %% "scalatest-shouldmatchers" % "3.2.5",
  "org.scalatest"         %% "scalatest-funspec"        % "3.2.5",
  "org.scalacheck"        %% "scalacheck"               % "1.15.3",
  "com.lihaoyi"           %% "upickle"                  % "1.2.3",
  "com.typesafe.akka"     %% "akka-actor-typed"         % "2.6.13",
  "com.typesafe.akka"     %% "akka-stream"              % "2.6.13",
  "com.typesafe.akka"     %% "akka-http"                % "10.2.4",
  "com.github.pureconfig" %% "pureconfig"               % "0.15.0",
  "org.scalaj"            %% "scalaj-http"              % "2.4.2"
)
