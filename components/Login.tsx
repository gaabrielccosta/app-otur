import { useState } from "react";
import React from "react";
import {
  Image,
  StyleSheet,
  Text,
  TextInput,
  TouchableOpacity,
  View,
} from "react-native";
import Icon from "react-native-vector-icons/FontAwesome";

interface LoginProps {
  username: string;
  password: string;
  setUsername: (username: string) => void;
  setPassword: (password: string) => void;
  setLoggedIn: (loggedIn: boolean) => void;
}

const Login: React.FC<LoginProps> = ({
  username,
  password,
  setUsername,
  setPassword,
  setLoggedIn,
}) => {
  const [showPass, setShowPass] = useState(false);
  const [wrongPassword, setWrongPassword] = useState(false);

  const login = () => {
    if (username === "admin" && password === "admin") {
      setLoggedIn(true);
      return;
    }
    setWrongPassword(true);
  };

  return (
    <>
      <Image
        source={require("../assets/logo.png")}
        style={styles.logo}
        resizeMode="contain"
      />

      <Text style={styles.title}>Faça seu Login</Text>

      <View style={styles.inputGroup}>
        <Icon name="user" size={20} color="#555" style={styles.icon} />
        <TextInput
          style={styles.input}
          placeholder="Usuário"
          value={username}
          onChangeText={setUsername}
          autoCapitalize="none"
          autoCorrect={false}
        />
      </View>

      <View style={styles.inputGroup}>
        <Icon name="lock" size={20} color="#555" style={styles.icon} />
        <TextInput
          style={styles.input}
          placeholder="Senha"
          value={password}
          onChangeText={setPassword}
          secureTextEntry={!showPass}
        />
        <TouchableOpacity onPress={() => setShowPass(!showPass)}>
          <Icon name={showPass ? "eye" : "eye-slash"} size={20} color="#555" />
        </TouchableOpacity>
      </View>

      <TouchableOpacity style={styles.button} onPress={login}>
        <Text style={styles.buttonText}>ENTRAR NO SISTEMA</Text>
      </TouchableOpacity>
      {wrongPassword && (
        <Text style={styles.wrongPasswordText}>
          Usuário ou senha incorretos!
        </Text>
      )}
    </>
  );
};

export default Login;

const styles = StyleSheet.create({
  logo: {
    width: 200,
    height: 180,
    alignSelf: "center",
    marginBottom: 24,
  },
  title: {
    fontWeight: "bold",
    fontSize: 20,
    marginBottom: 20,
    color: "#1B3764",
    alignSelf: "center",
  },
  inputGroup: {
    flexDirection: "row",
    alignItems: "center",
    borderColor: "#ccc",
    borderWidth: 1,
    borderRadius: 6,
    paddingHorizontal: 12,
    marginBottom: 16,
  },
  icon: {
    marginRight: 8,
  },
  input: {
    flex: 1,
    height: 44,
    fontSize: 16,
    color: "#333",
  },
  button: {
    backgroundColor: "#1B3764",
    height: 48,
    borderRadius: 6,
    justifyContent: "center",
    alignItems: "center",
    marginTop: 12,
  },
  buttonText: {
    color: "#fff",
    fontWeight: "600",
    fontSize: 16,
  },
  wrongPasswordText: {
    color: "red",
    fontWeight: "600",
    fontSize: 16,
  },
});
