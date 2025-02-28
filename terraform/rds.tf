resource "aws_db_subnet_group" "postgres_subnet" {
  name       = "postgres_subnet"
  subnet_ids = aws_subnet.private.*.id

  tags = {
    Name = "Postgres"
  }
}
resource "aws_db_instance" "myrds" {
  identifier             = "order"
  instance_class         = "db.t3.micro"
  allocated_storage      = 5
  engine                 = "postgres"
  engine_version         = "15"
  username               = var.db_username
  password               = var.db_password
  db_name                = var.db_name
  db_subnet_group_name   = aws_db_subnet_group.postgres_subnet.name
  vpc_security_group_ids = [aws_security_group.rds.id]
  parameter_group_name   = aws_db_parameter_group.postgrespg.name
  publicly_accessible    = false
  skip_final_snapshot    = true
}

resource "aws_db_parameter_group" "postgrespg" {
  name   = "postgrespg"
  family = "postgres15"

  parameter {
    name  = "log_connections"
    value = "1"
  }
}
