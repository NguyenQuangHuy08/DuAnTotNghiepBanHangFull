#Triển khai đóng gói và build ứng dụng
 B1: Cài đặt path cho maven ---> https://maven.apache.org/download.cgi (Chọn Binary zip archive rồi download)
 B2: Chạy command line ---> mvn clean package (Đóng gói build dự án vào file jar)
  Trong trường hợp muốn bỏ qua pentest của đóng gói ---> mvn clean package -DskipTests
 B3: Chạy command line ---> java -jar target/tên-file.jar
 
