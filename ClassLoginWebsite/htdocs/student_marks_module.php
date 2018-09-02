<?php
  $utorid = $_SESSION["login_user"];
  $sql = "SELECT * ";
  $sql = $sql . "FROM student_marks ";
  $sql = $sql . "WHERE utorid='" . $utorid . "'";
  $result = $conn->query($sql);
  if ($result->num_rows > 0){
    echo "<div class=\"material_title\">" . $_SESSION["first_name"] . "'s" . " Marks</div>";
    // Below is a table, so its in a container so that we can center it.
    echo "<div class=\"push_marks\">";

      // we want to show the headings
      // each "row" is a listed item
      // within each row is another list, which entries are listed items
      echo "<ul id=\"marks_container\">";
        echo "<li class=\"row\">";
          echo "<ul class=\"mark\">";
            // each heading is showing their UTORid, Material, what mark they got, and the weight
            echo "<span class=\"mark_restrict mark_title utorid\"><li>UTORid</li></span>";
            echo "<span class=\"mark_restrict mark_title\"><li>Material</li></span>";
            echo "<span class=\"mark_restrict mark_title\"><li>Mark</li></span>";
            echo "<span class=\"mark_restrict mark_title\"><li>Out of</li></span>";
            echo "<span class=\"mark_restrict mark_title weight\"><li>Weight</li></span>";
          echo "</ul>";
        echo "</li>";

        $final_mark = 0;
        while($row = $result->fetch_assoc()) {
          // define the utorid, material type and mark... material type is already defined
          // as contents
          $mark = $row["mark"];
          $content = $row["mat_type"];
          // now we can show what the mark is out of, and its weight
          // so we have to run another sql command
          $sql = "SELECT * ";
          $sql = $sql . "FROM course_materials ";
          $sql = $sql . "WHERE mat_type='" . $content . "'";
          $for_numbers = $conn->query($sql);  // different variable otherwise will crash
          $mat = $for_numbers->fetch_assoc();
          $out_of = $mat["out_of"];
          $weight = $mat["weight"];
          
          echo "<li class=\"row\">";
            echo "<ul class=\"mark\">";
              // we want to output each row that contains their utorid, material type, what mark they got and out of what mark
              echo "<li class=\"mark_restrict utorid\">" . $utorid . "</li>";
              echo "<li class=\"mark_restrict\">" . $content . "</li>";
              echo "<li class=\"mark_restrict\">" . $mark . "</li>";
              echo "<li class=\"mark_restrict\">" . $out_of . "</li>";
              echo "<li class=\"mark_restrict\">" . $weight . "</li></br>";
            echo "</ul>";
          echo "</li>";
          $final_mark = $final_mark + ($mark / $out_of) * $weight;
        }
        echo "Final mark: " . number_format($final_mark, 2);
      echo "</ul>";
  echo "</div>";
  }
?>