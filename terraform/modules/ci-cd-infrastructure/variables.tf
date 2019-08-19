variable "project_name" {}
variable "stage" {}
variable "log_level" {}
variable "repository" {}
variable "branch" {}
variable "artifact_bucket_name" {}
variable "get_items_lambda_name" {}
variable "tags" {
  description = "Globally used tags"
  default = {
    "Terraform"   = "true"
    "Project"     = "CDH"
    "Responsible" = "CTW"
  }
}
