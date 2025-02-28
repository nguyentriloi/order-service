provider "aws" {
  region = var.aws_region
  # profile = var.profile
#   shared_credentials_files = [ "/Users/interceptor/.aws/credentials" ]
  access_key = var.aws_access_key
  secret_key = var.aws_secret_key
}
