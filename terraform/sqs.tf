resource "aws_sqs_queue" "user_created_payment_queue" {
  name                      = "user_created_payment_queue"
  delay_seconds             = 0
  max_message_size          = 262144  # Maximum size of a message in bytes
  message_retention_seconds = 345600  # 4 days
  receive_wait_time_seconds = 0
}

resource "aws_sqs_queue" "user_updated_payment_queue" {
  name                      = "user_updated_payment_queue"
  delay_seconds             = 0
  max_message_size          = 262144  # Maximum size of a message in bytes
  message_retention_seconds = 345600  # 4 days
  receive_wait_time_seconds = 0
}

resource "aws_sqs_queue" "user_completed_payment_queue" {
  name                      = "user_completed_payment_queue"
  delay_seconds             = 0
  max_message_size          = 262144  # Maximum size of a message in bytes
  message_retention_seconds = 345600  # 4 days
  receive_wait_time_seconds = 0
}

output "queue_url" {
  value = aws_sqs_queue.user_created_payment_queue.id
}
