variable "project_name" {}
variable "stage" {}
variable "log_level" {}
variable "artifact_bucket_name" {}
variable "items_table" {}

variable "tags" {
  type = "map"
  description = "Globally used tags"
  default = {
    "Terraform" = "true"
    "Project" = "Example"
    "Responsible" = "Vitor"
  }
}