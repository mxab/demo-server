job "demo-server-docker" {

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
      driver = "docker"
      config {
        image = ""
        ports = ["http"]
      }
    }

  }
}