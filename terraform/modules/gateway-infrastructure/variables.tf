variable "project_name" {}
variable "stage" {}
variable "get_items_lambda_name" {}
variable "tags" {
  type = "map"
  description = "Globally used tags"
  default = {
    "Terraform" = "true"
    "Project" = "CDH"
    "Responsible" = "CTW"
  }
}