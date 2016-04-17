require 'dotenv/tasks'

task :version do
  sh "./gradlew version"
end

task :bump_major do
  sh "./gradlew bumpMajor"
  sh "./gradlew genReadMe"
end

task :bump_minor do
  sh "./gradlew bumpMinor"
  sh "./gradlew genReadMe"
end

task :bump_patch do
  sh "./gradlew bumpPatch"
  sh "./gradlew genReadMe"
end

task :build do
  sh "./gradlew clean build"
end

task upload: [:build, :dotenv] do
  sh "./gradlew library:bintrayUpload -PbintrayUser=#{ENV["BINTRAY_USERNAME"]} -PbintrayKey=#{ENV["BINTRAY_API_KEY"]} -PdryRun=false"
  sh "./gradlew compiler:bintrayUpload -PbintrayUser=#{ENV["BINTRAY_USERNAME"]} -PbintrayKey=#{ENV["BINTRAY_API_KEY"]} -PdryRun=false"
end
