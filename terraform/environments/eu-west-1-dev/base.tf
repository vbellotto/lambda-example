provider "aws" {
  profile = "cdh_dev"
  region = "eu-west-1"
}

terraform {
  backend "s3" {
    profile = "cdh_dev"
    region = "eu-west-1"
    bucket = "terraform-state-192272654777-eu-west-1"
    dynamodb_table = "terraform-state-192272654777-eu-west-1"
    key = "ssp-vss/eu-west-1/dev/terraform.tfstate"
  }
}

data "aws_region" "current" {}
