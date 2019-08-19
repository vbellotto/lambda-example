data "aws_region" "current" {}
data "aws_partition" "current" {}
data "aws_caller_identity" "current" {}

data "local_file" "deploy_lambda" {
  filename = "${path.module}/auto-deploy.zip"
}