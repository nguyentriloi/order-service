resource "aws_ssm_parameter" "DB_HOST" {
  name  = "/order/DB_HOST"
  type  = "String"
  value = aws_db_instance.myrds.address
}

resource "aws_ssm_parameter" "DB_PORT" {
  name  = "/order/DB_PORT"
  type  = "String"
  value = aws_db_instance.myrds.port
}

resource "aws_ssm_parameter" "DB_NAME" {
  name  = "/order/DB_NAME"
  type  = "String"
  value = var.db_name
}

resource "aws_ssm_parameter" "DB_USERNAME" {
  name  = "/order/DB_USERNAME"
  type  = "String"
  value = var.db_username
}

resource "aws_ssm_parameter" "DB_PASSWORD" {
  name  = "/order/DB_PASSWORD"
  type  = "SecureString"
  value = var.db_password
}
