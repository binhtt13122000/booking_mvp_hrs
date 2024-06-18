terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.16"
    }
  }
}

module "rds" {
  source              = "./rds"
  db_identifier       = "myp-system"
  db_name             = "mvp_db"
  db_user_name        = "mvp"
  security_group_name = "db-sg-mvp"
}

module "normal_param" {
  source = "./param-store"
  params = {
    "/mvp/booking/spring.application.name" : "mvp_hotel_booking"
    "/mvp/booking/spring.datasource.username" : module.rds.db_username,
    "/mvp/booking/spring.datasource.url" : format("jdbc:postgresql://%s:%s/%s", module.rds.db_address, module.rds.db_port, module.rds.db_name),

  }
}

module "secret_param" {
  source = "./param-store"
  params = {
    "/mvp/booking/spring.datasource.password" : module.rds.db_password,
    "/mvp/booking/jwt.secret" : "123453434sdasdzxZxZxsdasdsxXzXZXzmx>zxm.zLXXCMZXCZMXLAWDLMMXCMZXCMZX123132",
    "/mvp/booking/jwt.issuer" : "thanhbinh",
    "/mvp/booking/jwt.audience" : "thanhbinh",
    "/mvp/booking/jwt.expired" : "864000000",
  }
  type = "SecureString"
}