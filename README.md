# inventory-back
Repository of backend inventory project

## Create project
``` bash
mvn io.quarkus.platform:quarkus-maven-plugin:3.6.3:create \
    -DprojectGroupId=local.mateo \
    -DprojectArtifactId=inventory-control \
    -DclassName="local.mateo.InventoryResource" \
    -Dpath="/inventory"
```