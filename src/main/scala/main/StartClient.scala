package main

import akka.actor.{ActorSystem, Props}
import actors.Client
import com.typesafe.config.ConfigFactory


object StartClient {
  def main(args: Array[String]): Unit = {
    if (args.isEmpty) {
      println("Please provide IP of ChatBox Actor as the commandline argument")
      System.exit(0)
    }
    val config = ConfigFactory.load("client")
    val clientSystem = ActorSystem("CpepelientSystem", config)
    println("Enter your Nick Name:")
//    var name = Console.readLine.trim
    var name = scala.io.StdIn.readLine().trim
    while (name == "") {
      println("Enter your Nick Name:")
      name = scala.io.StdIn.readLine().trim
    }
    val client = clientSystem.actorOf(Props(new Client(name, args(0))), "Client")

    println("Type message end with -> after -> type name of the person to send " +
      "and hit enter to send messages")

    while (true) {
      val line = Console.readLine.trim
      if (line != "" && line.contains("->") && line.split("->").size == 2) {
        val texts = line.split("->")
        client ! Client.SendMessage(texts(1).trim, texts(0).trim)
      }
    }
  }
}
