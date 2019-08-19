provider "aws" {
  profile = "<enter your profile>"
  region = "eu-west-1"
}

terraform {
  backend "s3" {
    profile = "<enter your profile>"
    region = "eu-west-1"
    bucket = "<state bucket name>"
    dynamodb_table = "<state bucket table>"
    key = "item/eu-west-1/dev/terraform.tfstate"
  }
}

data "aws_region" "current" {}
