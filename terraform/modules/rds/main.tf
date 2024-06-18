## DB PASSWORD

resource "random_password" "db_password" {
  length           = 16
  special          = true
  override_special = "!#$%&*()-_=+[]{}<>:?"
}

## DB SECURITY GROUP

resource "aws_security_group" "db_security_group" {
  name        = var.security_group_name
  description = "Security group for DB"

  ingress {
    from_port = var.db_port
    to_port   = var.db_port
    protocol  = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port = 0
    to_port   = 0
    protocol  = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

## DB

resource "aws_db_instance" "db" {
  identifier          = var.db_identifier
  engine              = var.db_engine
  port                = var.db_port
  instance_class      = var.db_instance_class
  db_name             = var.db_name
  username            = var.db_user_name
  password            = random_password.db_password.result
  multi_az            = var.db_multi_az
  publicly_accessible = true
  vpc_security_group_ids = [aws_security_group.db_security_group.id]
  allocated_storage   = var.db_allocated_storage
  skip_final_snapshot = true

  tags = {
    Name = var.db_name
  }
}