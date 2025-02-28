# alb.tf

resource "aws_alb" "main" {
  name                       = "cb-load-balancer"
  internal                   = false
  load_balancer_type         = "application"
  subnets                    = aws_subnet.public.*.id
  security_groups            = [aws_security_group.lb.id]
  enable_deletion_protection = false
}

resource "aws_alb_target_group" "order" {
  name        = "order-service-target-group"
  port        = var.order_service_port
  protocol    = "HTTP"
  vpc_id      = aws_vpc.main.id
  target_type = "ip"

  health_check {
    healthy_threshold   = "3"
    interval            = "30"
    protocol            = "HTTP"
    matcher             = "200"
    timeout             = "3"
    path                = var.health_check_path
    unhealthy_threshold = "2"
  }
}

# Redirect all traffic from the ALB to the target group
resource "aws_alb_listener" "http_listener" {
  load_balancer_arn = aws_alb.main.arn
  port              = var.alb_port
  protocol          = "HTTP"

  default_action {
    target_group_arn = aws_alb_target_group.order.arn //default action to service template
    type             = "forward"
  }
}

# Create a listener rule for path-based routing
resource "aws_alb_listener_rule" "service_template_rule" {
  listener_arn = aws_alb_listener.http_listener.arn
  priority     = 100 # Adjust priority as needed

  action {
    type             = "forward"
    target_group_arn = aws_alb_target_group.order.arn
  }

  condition {
    path_pattern {
      values = ["/order-service/*"]
    }
  }
}
