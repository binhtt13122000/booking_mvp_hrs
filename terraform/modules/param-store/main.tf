resource "aws_ssm_parameter" "param" {
  for_each = var.params
  name     = each.key
  type     = var.type
  value    = each.value
}