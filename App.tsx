import React, { useState } from "react";
import { View, Text, StyleSheet } from "react-native";
import Login from "./components/Login";
import Formulario from "./components/Formulario";

const App = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [loggedIn, setLoggedIn] = useState(true);

  return (
    <View style={styles.container}>
      {!loggedIn && (
        <Login
          username={username}
          password={password}
          setLoggedIn={setLoggedIn}
          setUsername={setUsername}
          setPassword={setPassword}
        />
      )}
      {loggedIn && <Formulario />}
    </View>
  );
};

export default App;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 24,
    backgroundColor: "#fff",
    justifyContent: "center",
  },
});
