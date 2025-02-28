# variables.tf

variable "aws_access_key" {
    description = "The IAM public access key"
}

variable "aws_secret_key" {
    description = "IAM secret access key"
}

variable "aws_region" {
    description = "The AWS region things are created in"
}

variable "profile" {
    description = "The AWS profile things are created in"
    default = "default"
}

variable "ec2_task_execution_role_name" {
    description = "ECS task execution role name"
    default = "myEcsTaskExecutionRole"
}

variable "ecs_auto_scale_role_name" {
    description = "ECS auto scale role name"
    default = "myEcsAutoScaleRole"
}

variable "az_count" {
    description = "Number of AZs to cover in a given region"
    default = "2"
}

variable "app_image" {
    description = "Docker image to run in the ECS cluster"
    default = "333740472250.dkr.ecr.ap-southeast-1.amazonaws.com/order:latest"
}

variable "alb_port" {
    description = "Port exposed by the Alb to redirect traffic to"
    default = 80
}

variable "order_service_port" {
    description = "Port exposed by the Alb to redirect traffic to"
    default = 8080
}

variable "app_count" {
    description = "Number of docker containers to run"
    default = 1
}

variable "health_check_path" {
  default = "/actuator/health"
}

variable "fargate_cpu" {
    description = "Fargate instance CPU units to provision (1 vCPU = 1024 CPU units)"
    default = "1024"
}

variable "fargate_memory" {
    description = "Fargate instance memory to provision (in MiB)"
    default = "2048"
}

variable "db_password" {
  description = "RDS root user password"
  type        = string
  sensitive   = true
  default = "12345678"
}

variable "db_name" {
  description = "RDS root user password"
  type        = string
  default = "postgres"
}

variable "db_username" {
  description = "RDS root user password"
  type        = string
  default = "postgres"
}
