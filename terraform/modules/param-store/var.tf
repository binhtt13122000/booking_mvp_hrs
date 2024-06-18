# VARIABLES

variable "params" {
  description = "params"
  type = map(string)
}

variable "type" {
  description = "param type"
  type        = string
  default     = "String"
}

