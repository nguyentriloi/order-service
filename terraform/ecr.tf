#ecr.tf

resource "aws_ecr_repository" "coffee_order" {
  name                 = "coffee/order"
  image_tag_mutability = "MUTABLE"

  image_scanning_configuration {
    scan_on_push = true
  }
}

output "ecr_url" {
  value = aws_ecr_repository.coffee_order.repository_url
}
