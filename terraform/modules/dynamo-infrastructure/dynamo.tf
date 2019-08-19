resource "aws_dynamodb_table" "items_table" {
  name = "${var.project_name}-${data.aws_region.current.name}-${var.stage}-items"
  billing_mode = "PROVISIONED"
  read_capacity = 20
  write_capacity = 20
  hash_key = "typeOfItem"
  range_key = "itemId"
  stream_enabled = true
  stream_view_type = "NEW_IMAGE"
  attribute {
    name = "typeOfItem"
    type = "S"
  }
  attribute {
    name = "itemId"
    type = "S"
  }
  attribute {
    name = "itemStatus"
    type = "S"
  }
  global_secondary_index {
    name = "statusIndex"
    hash_key = "itemStatus"
    range_key = "itemId"
    projection_type = "ALL"
    write_capacity     = 20
    read_capacity      = 20
  }
  tags = "${merge(var.tags, map("Name", format("%s", "${var.project_name}-${data.aws_region.current.name}-${var.stage}-items")))}"
}
