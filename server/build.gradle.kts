plugins {
    application
}


group = "${rootProject.group}.server"


application {
    mainClass.set("$group.Main")
}


dependencies {
    val junitVersion: String by project

    testImplementation("org.junit.jupiter:junit-jupiter:$junitVersion")
}


tasks.test {
    useJUnitPlatform()
}
