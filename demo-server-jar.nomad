job "demo-server-jar" {

  datacenters = ["dc1"]
  type = "service"


  group "app" {
    network {
      mode = "bridge"
      port "http" {
        to = 8080
      }
    }


    task "app" {
      artifact {
        source      = "https://github.com/mxab/demo-server/releases/download/v0.3.0/demo-server-0.3.0-runner.jar"
        destination = "local/app"

      }
      driver = "java"
      config {
        jar_path    = "local/app/demo-server-0.3.0-runner.jar"
        jvm_options = ["-Xmx512m", "-Xms256m"]
      }

      resources {
        cpu    = 500 # 500 MHz
        memory = 512 # 512MB
      }

    }


    # register service at consul
    service {
      name = "demo-jar"
      tags = ["traefik.enable=true"] #this tag triggers traefik reverse proxy
      port = "http"
    }
  }
}