import com.github.javafaker.Faker
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.*
import kotlin.collections.*

plugins {
    id("org.springframework.boot") version "2.7.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
}

group = "mas"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.flywaydb:flyway-core")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

buildscript {

    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("com.github.javafaker", "javafaker", "1.0.2")
        classpath("com.squareup.okhttp3", "okhttp", "4.10.0")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.register("seedData") {

    doLast {

        var text = ""

        val file = File("./src/main/resources/db/migration/V2__seed-data.sql")
        file.createNewFile()

        repeat(100) {

            val request = Request.Builder()
                .url("https://loremflickr.com/640/480/business")
                .get()
                .build()

            val faker = Faker(Locale.CANADA)

            val companyId = UUID.randomUUID()
            val companyName = faker.company().name()
            val companyPhoneNumber = faker.phoneNumber().phoneNumber()
            val companyDescription = faker.lorem().words(50).joinToString(separator = " ", postfix = ".")
            val companyImage = OkHttpClient().newCall(request).execute().request.url.toString()


            val line = arrayListOf<String>(companyId.toString(), companyName, companyPhoneNumber, companyDescription, companyImage)
                .joinToString(separator = ", ", prefix = "INSERT INTO company VALUES(", postfix = ");") {
                    "'${it.replace("'", "''")}'"
                }

            text += "$line\n"
        }

        file.writeText(text)
    }
}
