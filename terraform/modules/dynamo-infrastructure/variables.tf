variable "project_name" {}
variable "stage" {}
variable "tags" {
  type = "map"
  description = "Globally used tags"
  default = {
    "Terraform" = "true"
    "Project" = "Example"
    "Responsible" = "Vitor"
  }
}