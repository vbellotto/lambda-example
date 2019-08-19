provider "aws" {
  profile = "<aws profile>"
  region = "eu-west-1"
}

// Uncoment the code bellow if you desire to keep a remote tfstate.

//terraform {
//  backend "s3" {
//    profile = "<enter your profile>"
//    region = "eu-west-1"
//    bucket = "<state bucket name>"
//    dynamodb_table = "<state bucket table>"
//    key = "item/eu-west-1/dev/terraform.tfstate"
//  }
//}

data "aws_region" "current" {}
