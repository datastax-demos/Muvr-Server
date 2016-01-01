import Dependencies._

enablePlugins(sbtdocker.DockerPlugin)

name := "main"

libraryDependencies ++= Seq(
  // Core Akka
  akka.actor,
  akka.cluster,
  akka.contrib,
  akka.persistence,
  akka.persistence_cassandra,
  akka.leveldb,
  // Codec
  scalaz.core,
  // Spray
  spray.routing,
  spray.can,
  spray.client,
  spray.json,
  // Apple push notifications
  apns,
  slf4j.slf4j_simple,
  // Serialization
  akka.chill,
  // Testing
  scalatest % "test",
  scalacheck % "test",
  akka.testkit % "test"
)

docker <<= (docker dependsOn assembly)

// Define a Dockerfile
dockerfile in docker := {
  val jarFile = (assemblyOutputPath in assembly).value
  val appDirPath = "/app"
  val jarTargetPath = s"$appDirPath/${jarFile.name}"

  new Dockerfile {
    from("java")
    // Expose port 8080
    expose(8080)

    add(jarFile, jarTargetPath)
    workDir(appDirPath)
    entryPoint("java", "-jar", jarTargetPath)
  }
}

imageName in docker := {
  ImageName(
    namespace = Some("muvr"),
    repository = "server",
    tag = Some(s"${name.value}-production"))
}

buildOptions in docker := BuildOptions(cache = false)
