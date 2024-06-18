# VARIABLES

variable "db_identifier" {
  default = "my-db-identifier"
  type    = string
}

variable "db_engine" {
  type    = string
  default = "postgres"
}

variable "db_port" {
  type    = string
  default = "5432"
}

variable "db_instance_class" {
  type    = string
  default = "db.t3.micro"
}

variable "db_name" {
  type    = string
  default = "mydbname"
}

variable "db_user_name" {
  type    = string
  default = "mydbusername"
}

variable "db_multi_az" {
  type    = bool
  default = false
}

variable "db_allocated_storage" {
  type    = number
  default = 20
}

variable "security_group_name" {
  type = string
}

