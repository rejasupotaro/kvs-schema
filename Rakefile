require 'dotenv/tasks'

desc "Show current version"
task :version do
  sh "./gradlew version"
end

desc "Bump major version"
task :bump_major do
  sh "./gradlew bumpMajor"
  sh "./gradlew genReadMe"
end

desc "Bump minor version"
task :bump_minor do
  sh "./gradlew bumpMinor"
  sh "./gradlew genReadMe"
end

desc "Bump patch version"
task :bump_patch do
  sh "./gradlew bumpPatch"
  sh "./gradlew genReadMe"
end

desc "Rebuild this project"
task :build do
  sh "./gradlew clean build"
end

desc "Upload this library to bintray"
task upload: [:build, :dotenv] do
  sh "./gradlew library:bintrayUpload -PbintrayUser=#{ENV["BINTRAY_USERNAME"]} -PbintrayKey=#{ENV["BINTRAY_API_KEY"]} -PdryRun=false"
  sh "./gradlew compiler:bintrayUpload -PbintrayUser=#{ENV["BINTRAY_USERNAME"]} -PbintrayKey=#{ENV["BINTRAY_API_KEY"]} -PdryRun=false"
end
