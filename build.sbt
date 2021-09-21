name := """stock-watcher"""
organization := "com.drakesobania"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.6"

libraryDependencies += guice
libraryDependencies += "com.google.code.gson" % "gson" % "2.8.8"