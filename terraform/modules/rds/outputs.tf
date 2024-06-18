# OUTPUTS

output "db_address" {
  value = aws_db_instance.db.address
  depends_on = [aws_db_instance.db]
}

output "db_username" {
  value = aws_db_instance.db.username
  depends_on = [aws_db_instance.db]
}

output "db_port" {
  value = aws_db_instance.db.port
  depends_on = [aws_db_instance.db]
}

output "db_name" {
  value = aws_db_instance.db.db_name
  depends_on = [aws_db_instance.db]
}

output "db_password" {
  value     = random_password.db_password.result
  sensitive = true
  depends_on = [aws_db_instance.db]
}