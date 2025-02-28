# iam.tf

resource "aws_iam_role" "ecs_task_execution_role" {
  name               = "ecsTaskExecutionRole"
  assume_role_policy = jsonencode({
    Version   = "2012-10-17"
    Statement = [
      {
        Effect    = "Allow"
        Principal = {
          Service = "ecs-tasks.amazonaws.com"
        }
        Action = "sts:AssumeRole"
      }
    ]
  })
}

# Create the policy to allow access to Parameter Store
resource "aws_iam_policy" "ecs_parameter_store_policy" {
  name        = "ecsParameterStorePolicy"
  description = "Policy for ECS tasks to access Parameter Store"

  policy = jsonencode({
    Version   = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Action = [
          "ssm:GetParameter",
          "ssm:GetParameters",
          "ssm:GetParametersByPath"
        ]
        Resource = "arn:aws:ssm:*:*:parameter/*"  // Update with your actual parameter ARN
      }
    ]
  })
}

resource "aws_iam_policy" "ecs_sqs_policy" {
  name        = "ecsSqsPolicy"
  description = "Policy for ECS tasks to access SQS"

  policy = jsonencode({
    Version   = "2012-10-17"
    Statement = [
      {
        Action = [
          "sqs:SendMessage",
          "sqs:ReceiveMessage",
          "sqs:DeleteMessage",
          "sqs:ListQueues",
          "sqs:GetQueueAttributes",
          "sqs:ChangeMessageVisibility",
          "sqs:CreateQueue"
        ]
        Effect   = "Allow"
        Resource = "arn:aws:sqs:*:*:*"
      }
    ]
  })
}

resource "aws_iam_role" "ecs_auto_scale_role" {
  name               = "ecsAutoScaleRole"
  assume_role_policy = jsonencode({
    Version   = "2012-10-17"
    Statement = [
      {
        Effect    = "Allow"
        Principal = {
          Service = "ecs.amazonaws.com"
        }
        Action = "sts:AssumeRole"
      },
    ]
  })
}

resource "aws_iam_role_policy_attachment" "ecs-task-execution-role-policy-attachment" {
  role       = aws_iam_role.ecs_task_execution_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}

# Attach the policy to the IAM role
resource "aws_iam_role_policy_attachment" "ecs_execution_role_attachment" {
  role       = aws_iam_role.ecs_task_execution_role.name
  policy_arn = aws_iam_policy.ecs_parameter_store_policy.arn
}

resource "aws_iam_role_policy_attachment" "ecs_sqs_policy_attachment" {
  role       = aws_iam_role.ecs_task_execution_role.name
  policy_arn = aws_iam_policy.ecs_sqs_policy.arn
}
